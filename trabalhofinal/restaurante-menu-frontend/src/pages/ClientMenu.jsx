import React, { useState, useEffect } from 'react';
import api from '../services/api';
import ProductCard from '../components/ProductCard';
import Cart from '../components/Cart';
import { useCart } from '../contexts/CartContext'; // Importe o hook do contexto

function ClientMenu() {
    const [products, setProducts] = useState([]);
    const { cartItems, clearCart } = useCart(); // Use o hook do contexto
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState('');

    useEffect(() => {
        fetchActiveProducts();
    }, []);

    const fetchActiveProducts = async () => {
        try {
            setMessage('');
            setMessageType('');
            const response = await api.get('/products');
            setProducts(response.data);
        } catch (error) {
            console.error('Erro ao buscar produtos ativos:', error);
            setMessage('Erro ao carregar cardápio. Tente novamente.');
            setMessageType('error');
        }
    };

    const handleCheckout = async () => {
        if (cartItems.length === 0) {
            setMessage('Seu carrinho está vazio!');
            setMessageType('error');
            return;
        }

        const customAlert = (messageText, type) => {
            return new Promise((resolve) => {
                const dialog = document.createElement('div');
                dialog.className = 'custom-dialog-overlay';
                dialog.innerHTML = `
            <div class="custom-dialog-content">
                <p class="${type === 'success' ? 'message-success' : 'message-error'}">${messageText}</p>
                <div class="custom-dialog-buttons">
                <button class="custom-dialog-button custom-dialog-ok button-primary">OK</button>
                </div>
            </div>
            `;
                document.body.appendChild(dialog);

                dialog.querySelector('.custom-dialog-ok').onclick = () => {
                    document.body.removeChild(dialog);
                    resolve();
                };
            });
        };

        try {
            const orderItems = cartItems.map(item => ({
                productId: item.id,
                quantity: item.quantity,
                // priceAtOrder não precisa ser enviado, o backend buscará o preço atual
            }));

            const response = await api.post('/orders', { items: orderItems });
            await customAlert('Pedido finalizado com sucesso! ID do Pedido: ' + response.data.id, 'success');
            clearCart(); // Limpa o carrinho após a finalização
            setMessage('Pedido finalizado com sucesso!');
            setMessageType('success');
        } catch (error) {
            console.error('Erro ao finalizar pedido:', error);
            await customAlert('Erro ao finalizar pedido. Tente novamente.', 'error');
            setMessage('Erro ao finalizar pedido. Verifique o console para mais detalhes.');
            setMessageType('error');
        }
    };

    return (
        <div className="client-menu-container">
            <h2 className="section-title">Cardápio do Restaurante</h2>

            {message && (
                <div className={`message ${messageType === 'success' ? 'message-success' : 'message-error'}`}>
                    {message}
                </div>
            )}

            <div className="client-content-grid">
                {/* Coluna para o cardápio */}
                <div className="menu-products-section">
                    <h3 className="subsection-title">Pratos Disponíveis</h3>
                    <div className="product-grid">
                        {products.map((product) => (
                            <ProductCard key={product.id} product={product} />
                        ))}
                        {products.length === 0 && (
                            <p className="empty-message">Nenhum produto ativo encontrado no cardápio.</p>
                        )}
                    </div>
                </div>

                {/* Coluna para o carrinho */}
                <div className="cart-section">
                    <Cart onCheckout={handleCheckout} />
                </div>
            </div>
            <style>{`
        /* Custom Dialog for Alert/Confirmation */
        .custom-dialog-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }

        .custom-dialog-content {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            text-align: center;
            max-width: 400px;
            width: 90%;
        }

        .custom-dialog-content p {
            margin-bottom: 25px;
            font-size: 18px;
            color: #333;
        }

        .custom-dialog-buttons {
            display: flex;
            justify-content: center;
            gap: 15px;
        }

        .custom-dialog-button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        .custom-dialog-cancel {
            background-color: #ccc;
            color: #333;
        }

        .custom-dialog-cancel:hover {
            background-color: #bbb;
        }

      `}</style>
        </div>
    );
}

export default ClientMenu;