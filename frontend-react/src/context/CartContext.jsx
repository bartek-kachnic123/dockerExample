import {createContext, useContext} from 'react';
export const CartContext = createContext(undefined);
export const useCart = () => useContext(CartContext);
