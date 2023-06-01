import ReactDOM from 'react-dom/client';
import Layout from './Components/LayoutArea/Layout/Layout';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import { createTheme, ThemeProvider } from '@mui/material/styles';

const theme = createTheme({
  direction: 'rtl',
})
const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  // <React.StrictMode>
  <ThemeProvider theme={theme}>
    <BrowserRouter>
      <Layout />
    </BrowserRouter>
  </ThemeProvider>

  // </React.StrictMode>
);
reportWebVitals();
