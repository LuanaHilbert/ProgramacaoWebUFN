import React, { useState } from 'react';
import AdminDashboard from './pages/AdminDashboard';
import ClientMenu from './pages/ClientMenu';
import { CartProvider } from './contexts/CartContext'; // Importe o CartProvider

function App() {
    const [isAdmin, setIsAdmin] = useState(false); // Estado para alternar entre admin/cliente

    return (
        <CartProvider> {/* Envolve toda a aplicação com o provedor do carrinho */}
            <div className="app-container">
                {/* Barra de Navegação/Toggle para Admin/Cliente */}
                <nav className="navbar">
                    <h1 className="navbar-title">Menu do Restaurante</h1>
                    <div>
                        <button
                            onClick={() => setIsAdmin(!isAdmin)}
                            className="navbar-button"
                        >
                            {isAdmin ? 'Ir para Área do Cliente' : 'Ir para Área Administrativa'}
                        </button>
                    </div>
                </nav>

                {/* Renderiza o componente com base no estado isAdmin */}
                <main className="main-content">
                    {isAdmin ? <AdminDashboard /> : <ClientMenu />}
                </main>
            </div>
        </CartProvider>
    );
}

export default App;