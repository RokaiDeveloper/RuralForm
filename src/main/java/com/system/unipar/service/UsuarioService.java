package com.system.unipar.service;

import com.system.unipar.model.Usuario;
import com.system.unipar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UsuarioService {

    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        try {
            return usuarioRepository.findAll();
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar todos os usuários", e);
            return List.of();
        }
    }

    public Optional<Usuario> findById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido: " + id);
                return Optional.empty();
            }
            return usuarioRepository.findById(id);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar usuário por ID: " + id, e);
            return Optional.empty();
        }
    }

    public Usuario save(Usuario usuario) {
        try {
            if (usuario == null) {
                logger.warning("Usuário nulo fornecido para salvar");
                return null;
            }
            return usuarioRepository.save(usuario);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao salvar usuário", e);
            return null;
        }
    }

    public Usuario update(Long id, Usuario usuario) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido para atualização: " + id);
                return null;
            }
            if (usuario == null) {
                logger.warning("Usuário nulo fornecido para atualização");
                return null;
            }
            if (usuarioRepository.existsById(id)) {
                usuario.setId(id);
                return usuarioRepository.save(usuario);
            }
            logger.warning("Usuário não encontrado para atualização com ID: " + id);
            return null;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar usuário com ID: " + id, e);
            return null;
        }
    }

    public boolean deleteById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido para exclusão: " + id);
                return false;
            }
            if (usuarioRepository.existsById(id)) {
                usuarioRepository.deleteById(id);
                return true;
            }
            logger.warning("Usuário não encontrado para exclusão com ID: " + id);
            return false;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao excluir usuário com ID: " + id, e);
            return false;
        }
    }

    public boolean existsById(Long id) {
        try {
            if (id == null || id <= 0) {
                return false;
            }
            return usuarioRepository.existsById(id);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao verificar existência do usuário com ID: " + id, e);
            return false;
        }
    }
}
