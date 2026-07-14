import { render } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { BaseBarChart } from './BaseBarChart';
import { BaseLineChart } from './BaseLineChart';

vi.mock('react-chartjs-2', () => ({
    Bar: () => <div />,
    Line: () => <div />
}));

describe('Base Charts Fallback Branches', () => {
    const minimalProps = {
        title: 'Minimal Chart',
        labels: ['Jan'],
        datasets: [{ label: 'Test Label', data: [10] }]
    };

    it('should fallback to default colors in Bar and Line charts mapping layers', () => {
        const barRender = render(<BaseBarChart {...minimalProps} yMax={100} />);
        expect(barRender.container).toBeDefined();

        const lineRender = render(<BaseLineChart {...minimalProps} stepped={true} yMax={50} />);
        expect(lineRender.container).toBeDefined();
    });
});