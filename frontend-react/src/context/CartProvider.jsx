import React, { useState } from 'react';
import {CartContext} from './CartContext';
export const CartProvider = ({ children }) => {
    const [selectedProducts, setSelectedProducts] = useState([]);

    const addProduct = (product) => {
        setSelectedProducts(prev => [...prev, product]);
    };

    const removeProduct = (productId) => {
        setSelectedProducts(prev => {
            const index = prev.findIndex(p => p.id === productId);
            if (index === -1) return prev;
            return [...prev.slice(0, index), ...prev.slice(index + 1)];
        });
    };

    return (
        <CartContext.Provider value={{ selectedProducts, addProduct, removeProduct }}>
            {children}
        </CartContext.Provider>
    );
};
