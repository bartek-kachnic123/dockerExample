import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from './features/common/components/Navbar.jsx';

export default function AppLayout() {
  return (
      <>
        <Navbar />
        <main className="p-6">
          <Outlet />
        </main>
      </>
  );
}
