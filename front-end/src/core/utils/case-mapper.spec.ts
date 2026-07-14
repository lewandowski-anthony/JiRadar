import { describe, it, expect } from 'vitest';
import { snakeToCamel, mapKeysToCamel } from './case-mapper';

describe('case-mapper', () => {
    describe('snakeToCamel', () => {
        it('should convert snake_case to camelCase', () => {
            expect(snakeToCamel('hello_world')).toBe('helloWorld');
            expect(snakeToCamel('some_very_long_key')).toBe('someVeryLongKey');
        });

        it('should convert kebab-case to camelCase', () => {
            expect(snakeToCamel('hello-world')).toBe('helloWorld');
        });
    });

    describe('mapKeysToCamel', () => {
        it('should recursively map object keys to camelCase', () => {
            const input = {
                first_name: 'John',
                address_info: {
                    zip_code: '75000',
                    city_name: 'Paris'
                }
            };

            const expected = {
                firstName: 'John',
                addressInfo: {
                    zipCode: '75000',
                    cityName: 'Paris'
                }
            };

            expect(mapKeysToCamel(input)).toEqual(expected);
        });

        it('should map elements inside an array', () => {
            const input = [
                { item_id: 1, item_name: 'A' },
                { item_id: 2, item_name: 'B' }
            ];

            const expected = [
                { itemId: 1, itemName: 'A' },
                { itemId: 2, itemName: 'B' }
            ];

            expect(mapKeysToCamel(input)).toEqual(expected);
        });

        it('should return raw value if not an array or object', () => {
            expect(mapKeysToCamel('raw_string')).toBe('raw_string');
            expect(mapKeysToCamel(123)).toBe(123);
            expect(mapKeysToCamel(null)).toBeNull();
        });
    });
});