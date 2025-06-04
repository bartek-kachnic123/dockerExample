import { Link, useLocation } from 'react-router-dom';
import React from 'react';

export default function Navbar() {
  const location = useLocation();

  const navItems = [
    { path: '/', label: 'Home' },
    { path: '/auth/login', label: 'Login' },
    { path: '/auth/register', label: 'Register' },
  ];

  return (
      <nav className="bg-gray-800 text-white p-4 flex gap-4">
        {navItems.map((item) => (
            <Link
                key={item.path}
                to={item.path}
                className={`hover:underline ${
                    location.pathname === item.path ? 'font-bold underline' : ''
                }`}
            >
              {item.label}
            </Link>
        ))}
      </nav>
  );
}
