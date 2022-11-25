import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter } from "react-router-dom";
import { ThemeProvider, createTheme } from '@mui/material/styles';

import App from './App'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
)
const theme = createTheme({
  palette: {
    primary: {
      main: '#FF2B4E',
    },
    secondary: {
      main: '#FFA200',
    },
    error: {
      main: '#D80D3D',
    },
    background: {
      default: '#ECF0F1',
    },
  }
})
