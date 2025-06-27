import React from 'react';
import { useCart } from '../contexts/CartContext';

function Cart({ onCheckout }) {
    const { cartItems, removeFromCart, updateCartQuantity } = useCart();

    const calculateTotal = () => {
        return cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
    };

    return (
        <div className="cart-container">
            <h3 className="cart-header">
                Carrinho de Compras
                <span className="cart-item-count">{cartItems.length}</span>
            </h3>
            {cartItems.length === 0 ? (
                <p className="cart-empty-message">Seu carrinho está vazio.</p>
            ) : (
                <>
                    <ul className="cart-items-list">
                        {cartItems.map((item) => (
                            <li key={item.id} className="cart-item">
                                <div className="cart-item-details">
                                    <p className="cart-item-name">{item.name}</p>
                                    <p className="cart-item-price-qty">R$ {item.price.toFixed(2)} x {item.quantity}</p>
                                </div>
                                <div className="cart-item-actions">
                                    <input
                                        type="number"
                                        min="1"
                                        value={item.quantity}
                                        onChange={(e) => updateCartQuantity(item.id, parseInt(e.target.value))}
                                        className="cart-item-quantity-input"
                                    />
                                    <button
                                        onClick={() => removeFromCart(item.id)}
                                        className="cart-item-remove-button"
                                    >
                                        Remover
                                    </button>
                                </div>
                            </li>
                        ))}
                    </ul>
                    <div className="cart-total">
                        <span>Total:</span>
                        <span>R$ {calculateTotal().toFixed(2)}</span>
                    </div>
                    <button
                        onClick={onCheckout}
                        className="cart-checkout-button"
                    >
                        Finalizar Compra
                    </button>
                </>
            )}
        </div>
    );
}

export default Cart;