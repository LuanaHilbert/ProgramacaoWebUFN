import React, { useState, useEffect } from 'react';
import api from '../services/api';
import ProductForm from '../components/ProductForm.jsx';

function AdminDashboard() {
    const [products, setProducts] = useState([]);
    const [editingProduct, setEditingProduct] = useState(null);
    const [isFormOpen, setIsFormOpen] = useState(false);
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState(''); // 'success' or 'error'

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = async () => {
        try {
            setMessage('');
            setMessageType('');
            const response = await api.get('/products/admin');
            setProducts(response.data);
        } catch (error) {
            console.error('Erro ao buscar produtos:', error);
            setMessage('Erro ao carregar produtos. Tente novamente.');
            setMessageType('error');
        }
    };

    const handleDelete = async (id) => {
        const customConfirm = (messageText) => {
            return new Promise((resolve) => {
                const dialog = document.createElement('div');
                dialog.className = 'custom-dialog-overlay';
                dialog.innerHTML = `
          <div class="custom-dialog-content">
            <p>${messageText}</p>
            <div class="custom-dialog-buttons">
              <button class="custom-dialog-button custom-dialog-cancel">Cancelar</button>
              <button class="custom-dialog-button custom-dialog-confirm button-danger">Confirmar</button>
            </div>
          </div>
        `;
                document.body.appendChild(dialog);

                dialog.querySelector('.custom-dialog-cancel').onclick = () => {
                    document.body.removeChild(dialog);
                    resolve(false);
                };
                dialog.querySelector('.custom-dialog-confirm').onclick = () => {
                    document.body.removeChild(dialog);
                    resolve(true);
                };
            });
        };

        const confirmed = await customConfirm('Tem certeza que deseja excluir este produto?');
        if (confirmed) {
            try {
                await api.delete(`/products/${id}`);
                setMessage('Produto excluído com sucesso!');
                setMessageType('success');
                fetchProducts(); // Recarrega a lista após a exclusão
            } catch (error) {
                console.error('Erro ao excluir produto:', error);
                setMessage('Erro ao excluir produto. Tente novamente.');
                setMessageType('error');
            }
        }
    };


    const handleToggleStatus = async (id, currentStatus) => {
        try {
            await api.patch(`/products/${id}/status?active=${!currentStatus}`);
            setMessage(`Status do produto alterado para ${!currentStatus ? 'Ativo' : 'Inativo'}.`);
            setMessageType('success');
            fetchProducts(); // Recarrega a lista após a mudança de status
        } catch (error) {
            console.error('Erro ao alterar status do produto:', error);
            setMessage('Erro ao alterar status do produto. Tente novamente.');
            setMessageType('error');
        }
    };

    const handleSaveProduct = () => {
        fetchProducts(); // Recarrega a lista após salvar/atualizar
        setEditingProduct(null);
        setIsFormOpen(false);
    };

    return (
        <div className="admin-dashboard-container">
            <h2 className="section-title">Área Administrativa - Gerenciar Produtos</h2>

            {message && (
                <div className={`message ${messageType === 'success' ? 'message-success' : 'message-error'}`}>
                    {message}
                </div>
            )}

            <button
                onClick={() => { setEditingProduct(null); setIsFormOpen(true); }}
                className="button button-success"
            >
                Cadastrar Novo Produto
            </button>

            {isFormOpen && (
                <div className="product-form-section">
                    <ProductForm product={editingProduct} onSave={handleSaveProduct} onClose={() => setIsFormOpen(false)} />
                </div>
            )}

            <h3 className="subsection-title">Lista de Produtos</h3>
            <div className="table-responsive">
                <table className="data-table">
                    <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Categoria</th>
                        <th>Preço</th>
                        <th>Status</th>
                        <th style={{ textAlign: 'center' }}>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    {products.map((product) => (
                        <tr key={product.id}>
                            <td data-label="Nome">{product.name}</td>
                            <td data-label="Categoria">{product.category}</td>
                            <td data-label="Preço">R$ {product.price.toFixed(2)}</td>
                            <td data-label="Status">
                  <span className={`product-status ${product.active ? 'status-active' : 'status-inactive'}`}>
                    {product.active ? 'Ativo' : 'Inativo'}
                  </span>
                            </td>
                            <td data-label="Ações" style={{ textAlign: 'center' }}>
                                <div className="button-group-table">
                                    <button
                                        onClick={() => { setEditingProduct(product); setIsFormOpen(true); }}
                                        className="button button-warning"
                                    >
                                        Editar
                                    </button>
                                    <button
                                        onClick={() => handleDelete(product.id)}
                                        className="button button-danger"
                                    >
                                        Excluir
                                    </button>
                                    <button
                                        onClick={() => handleToggleStatus(product.id, product.active)}
                                        className={`button ${product.active ? 'button-secondary' : 'button-info'}`}
                                    >
                                        {product.active ? 'Desativar' : 'Ativar'}
                                    </button>
                                </div>
                            </td>
                        </tr>
                    ))}
                    {products.length === 0 && (
                        <tr>
                            <td colSpan="5" style={{ textAlign: 'center', padding: '20px' }}>Nenhum produto cadastrado.</td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
            <style>{`
        /* Custom Dialog for Confirm */
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

        .button-group-table {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 5px;
        }

        @media (max-width: 767px) {
            .table-responsive {
                overflow-x: auto;
            }

            .data-table {
                width: 100%;
                display: block;
                overflow-x: auto; /* Força a rolagem horizontal para tabelas */
                white-space: nowrap; /* Impede que o conteúdo da célula quebre */
            }

            .data-table tbody {
                display: block;
                width: 100%;
            }

            .data-table tr {
                display: inline-block; /* Altera para inline-block para permitir rolagem */
                width: 100%; /* Ocupa a largura total */
                vertical-align: top;
                border-bottom: none; /* Remove a borda inferior do TR em mobile */
            }

            .data-table td {
                display: block;
                width: 100%; /* Cada célula ocupa a largura total do TR */
                text-align: left; /* Alinha o texto à esquerda */
                padding: 8px 10px; /* Reduz padding */
                position: relative;
                box-sizing: border-box; /* Inclui padding e border no width */
                white-space: normal; /* Permite quebra de linha dentro da célula */
            }

            .data-table td::before {
                content: attr(data-label);
                font-weight: bold;
                margin-right: 10px;
                color: #555;
                display: block;
                text-align: left;
                width: auto;
                position: static;
                padding-right: 0;
            }

            .button-group-table {
                flex-direction: column;
                gap: 8px;
            }

            .button-group-table .button {
                width: 100%; /* Botões ocupam largura total */
            }
        }
      `}</style>
        </div>
    );
}

export default AdminDashboard;