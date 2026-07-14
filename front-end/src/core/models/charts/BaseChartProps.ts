export interface BaseChartProps {
    title: string;
    labels: string[];
    datasets: {
        label: string;
        borderColor?: string;
        backgroundColor?: string;
        borderWidth?: number;
        data: number[];
    }[];
    yMax?: number;
    maxWidth?: string;
}