import PropTypes from 'prop-types';
import ReactApexChart from 'react-apexcharts';
// @mui
import { Card, CardHeader, Box } from '@mui/material';
// components
import BaseOptionChart from './BaseOptionChart';

// ----------------------------------------------------------------------

EmployeeChart.propTypes = {
  title: PropTypes.string,
  subheader: PropTypes.node,
  chartData: PropTypes.array.isRequired,
  chartLabels: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default function EmployeeChart({ title, subheader, chartLabels, chartData, ...other }) {
  function isObject(item) {
    return (item && typeof item === 'object' && !Array.isArray(item));
  }

  function mergeDeep(target, ...sources) {
    if (!sources.length) return target;
    const source = sources.shift();
  
    if (isObject(target) && isObject(source)) {
      for (const key in source) {
        if (isObject(source[key])) {
          if (!target[key]) Object.assign(target, { [key]: {} });
          mergeDeep(target[key], source[key]);
        } else {
          Object.assign(target, { [key]: source[key] });
        }
      }
    }
  
    return mergeDeep(target, ...sources);
  }

  const option = {
    plotOptions: { bar: { columnWidth: '16%' } },
    fill: { type: chartData.map((i) => i.fill) },
    labels: chartLabels,
    xaxis: { type: 'datetime' },
    tooltip: {
      shared: true,
      intersect: false,
      y: {
        formatter: (y) => {
          if (typeof y !== 'undefined') {
            return `${y.toFixed(0)}`;
          }
          return y;
        },
      },
      x : {
        format: "dd MMM"
      }
    },
    chart: {
      toolbar: {
        id: 'employee-data',
        show: true,
        offsetX: 0,
        offsetY: -80,
        tools: {
          download: true,
          customIcons: []
        },
        export: {
          csv: {
            filename: "Employee Monthly Statistics",
            columnDelimiter: ',',
            headerCategory: 'category',
            headerValue: 'value',
            dateFormatter(timestamp) {
              return new Date(timestamp).toDateString()
            }
          },
          svg: {
            filename: "Employee Monthly Statistics",
          },
          png: {
            filename: "Employee Monthly Statistics",
          }
        },
      },
    }
  }

  const chartOptions = mergeDeep(BaseOptionChart(), option);

  return (
    <Card {...other} sx={{ m: 2 }}>
        <CardHeader title={title} subheader={subheader} />
      
        <Box sx={{ p: 3, pb: 1 }} dir="ltr">
        <div id="chart">
            <div id="chart-timeline">
                <ReactApexChart type="line" series={chartData} options={chartOptions} height={364} />
            </div>
        </div>
        </Box>
    </Card>
  );
}
