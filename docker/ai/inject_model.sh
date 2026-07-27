#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
NC='\033[0m'

MODEL_FILE=""
MODEL_NAME="llama3.1"
OLLAMA_CONTAINER="jiradar-ollama"

usage() {
    echo "Usage: $0 --file <file.gguf> [--container <container_name>] [--model <model_name>]"
    echo "  -f, --file       Path to the .gguf model file (Required)"
    echo "  -c, --container  Name of the Ollama container (Default: jiradar-ollama)"
    echo "  -m, --model      Name to assign to the model in Ollama (Default: llama3.1)"
    exit 1
}

while [[ $# -gt 0 ]]; do
    case $1 in
        -f|--file)
            MODEL_FILE="$2"
            shift 2
            ;;
        -c|--container)
            OLLAMA_CONTAINER="$2"
            shift 2
            ;;
        -m|--model)
            MODEL_NAME="$2"
            shift 2
            ;;
        -h|--help)
            usage
            ;;
        *)
            echo -e "${RED}Unknown option: $1${NC}"
            usage
            ;;
    esac
done

if [ -z "$MODEL_FILE" ]; then
    echo -e "${RED}Error: The --file parameter is required.${NC}"
    usage
fi

FILE_BASE_NAME=$(basename "$MODEL_FILE")
FILE_DIR_NAME=$(dirname "$MODEL_FILE")
ABS_FILE_DIR=$(cd "$FILE_DIR_NAME" && pwd)

echo -e "${BLUE}Searching for the volume attached to '$OLLAMA_CONTAINER'...${NC}"
VOLUME_NAME=$(docker inspect "$OLLAMA_CONTAINER" --format '{{ range .Mounts }}{{ if eq .Destination "/root/.ollama" }}{{ .Name }}{{ end }}{{ end }}' 2>/dev/null)

if [ -z "$VOLUME_NAME" ]; then
    echo -e "${YELLOW}Container is stopped or not found, searching for volume by name pattern...${NC}"
    VOLUME_NAME=$(docker volume ls -q | grep "ollama-data" | head -n 1)
fi

if [ -z "$VOLUME_NAME" ]; then
    echo -e "${RED}Error: Could not locate the Docker volume for Ollama.${NC}"
    exit 1
fi
echo -e "${BLUE}Target volume detected: $VOLUME_NAME${NC}"

if [ ! -f "$ABS_FILE_DIR/$FILE_BASE_NAME" ]; then
    echo -e "${RED}Error: File '$MODEL_FILE' not found.${NC}"
    exit 1
fi

echo -e "${BLUE}Copying $FILE_BASE_NAME into the Docker volume...${NC}"
docker run --rm -v "$VOLUME_NAME":/target -v "$ABS_FILE_DIR":/source alpine cp "/source/$FILE_BASE_NAME" /target/

if [ $? -ne 0 ]; then
    echo -e "${RED}Error during file transfer to the Docker volume.${NC}"
    exit 1
fi
echo -e "${GREEN}File copied successfully.${NC}"

if [ ! "$(docker ps -q -f name=^/${OLLAMA_CONTAINER}$)" ]; then
    echo -e "${YELLOW}Container '$OLLAMA_CONTAINER' is not currently running.${NC}"
    echo "Please start your docker-compose stack and run the Ollama registration command manually."
    exit 0
fi

echo -e "${BLUE}Registering model '$MODEL_NAME' inside Ollama...${NC}"

docker exec -i "$OLLAMA_CONTAINER" sh <<-EOF
  cat << 'ModelfileEOF' > /root/.ollama/Modelfile
FROM /root/.ollama/$FILE_BASE_NAME
SYSTEM "You are a helpful AI assistant. Respond concisely, directly, and strictly adhere to the formatting and structural constraints requested by the user."
PARAMETER num_ctx 16384
ModelfileEOF
EOF

docker exec -it "$OLLAMA_CONTAINER" ollama create "$MODEL_NAME" -f /root/.ollama/Modelfile

echo -e "${GREEN}Done! The model '$MODEL_NAME' is active with a 16k context window and ready to use.${NC}"