import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import HomePage from './pages/HomePage.jsx';
import authRoutes from './features/auth/routes.jsx';
import AppLayout from './AppLayout.jsx';


export default function AppRouter() {
  const router = createBrowserRouter([
    {
      path: '/',
      element: <AppLayout />,
      children: [
        { index: true, element: <HomePage /> },
        {
          path: 'auth',
          children: authRoutes,
        },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
}
