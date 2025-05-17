-- Criação do banco de dados

CREATE DATABASE IF NOT EXISTS condominio
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE condominio;

-- Tabela de usuários
CREATE TABLE usuarios (
                          id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          tipo_usuario ENUM('condomino', 'sindico') NOT NULL,
                          cpf VARCHAR(14) NOT NULL UNIQUE,
                          data_nascimento DATE,
                          data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
                          status_aprovacao ENUM('pendente', 'aprovado', 'reprovado') DEFAULT 'pendente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de áreas comuns
CREATE TABLE areas_comuns (
                              id_area INT AUTO_INCREMENT PRIMARY KEY,
                              nome_area VARCHAR(100) NOT NULL,
                              descricao TEXT,
                              disponibilidade BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de reservas
CREATE TABLE reservas (
                          id_reserva INT AUTO_INCREMENT PRIMARY KEY,
                          id_usuario INT NOT NULL,
                          id_area INT NOT NULL,
                          data_reserva DATETIME NOT NULL,
                          status_reserva ENUM('pendente', 'aprovada', 'rejeitada') DEFAULT 'pendente',
                          observacoes TEXT,
                          CONSTRAINT fk_usuarios_reservas FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
                          CONSTRAINT fk_areas_comuns_reservas FOREIGN KEY (id_area) REFERENCES areas_comuns(id_area)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de mudanças
CREATE TABLE mudancas (
                          id_mudanca INT AUTO_INCREMENT PRIMARY KEY,
                          id_usuario INT NOT NULL,
                          data_mudanca DATETIME NOT NULL,
                          status_mudanca ENUM('pendente', 'aprovada', 'rejeitada') DEFAULT 'pendente',
                          observacoes TEXT,
                          CONSTRAINT fk_usuarios_mudancas FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de documentos
CREATE TABLE documentos (
                            id_documento INT AUTO_INCREMENT PRIMARY KEY,
                            titulo VARCHAR(100) NOT NULL,
                            descricao TEXT,
                            caminho_arquivo VARCHAR(255) NOT NULL,
                            data_upload DATETIME DEFAULT CURRENT_TIMESTAMP,
                            id_usuario INT NOT NULL,
                            CONSTRAINT fk_usuarios_documentos FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de visualizações de documentos
CREATE TABLE visualizacoes_documentos (
                                          id_visualizacao INT AUTO_INCREMENT PRIMARY KEY,
                                          id_usuario INT NOT NULL,
                                          id_documento INT NOT NULL,
                                          data_visualizacao DATETIME DEFAULT CURRENT_TIMESTAMP,
                                          CONSTRAINT fk_usuarios_visualizacoes FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
                                          CONSTRAINT fk_documentos_visualizacoes FOREIGN KEY (id_documento) REFERENCES documentos(id_documento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Cria um usuário administrador padrão
INSERT INTO usuarios (nome, email, senha, tipo_usuario, cpf, data_nascimento, status_aprovacao)
VALUES ('Administrador', 'teste.condominio@gmail.com', 'admin123', 'sindico', '12345678901', '1990-01-01', 'aprovado');