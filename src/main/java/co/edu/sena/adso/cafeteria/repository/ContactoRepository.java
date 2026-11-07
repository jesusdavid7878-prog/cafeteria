package co.edu.sena.adso.cafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.sena.adso.cafeteria.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}
