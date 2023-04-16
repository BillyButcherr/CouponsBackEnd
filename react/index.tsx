import React from 'react';
import ReactDOM from 'react-dom/client';
import Layout from './Components/LayoutArea/Layout/Layout';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  // <React.StrictMode>
  <BrowserRouter>
      <Layout/> 
  </BrowserRouter>
  // </React.StrictMode>
);
reportWebVitals();
