package co.edu.sena.adso.cafeteria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.edu.sena.adso.cafeteria.model.Contacto;
import co.edu.sena.adso.cafeteria.repository.ContactoRepository;

@Controller
public class ContactoController {

    private final ContactoRepository contactos;

    public ContactoController(ContactoRepository contactos) {
        this.contactos = contactos;
    }

    @GetMapping("/contacto")
    public String formulario(Model model) {
        model.addAttribute("contacto", new Contacto());
        return "contacto-form";
    }

    @PostMapping("/contacto")
    public String enviar(@RequestParam("nombre") String nombre,
                         @RequestParam("correo") String correo,
                         @RequestParam("asunto") String asunto,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        Contacto contacto = new Contacto();
        contacto.setNombre(nombre.trim());
        contacto.setCorreo(correo.trim());
        contacto.setAsunto(asunto.trim());

        if (!esValido(contacto)) {
            model.addAttribute("contacto", contacto);
            model.addAttribute("errorContacto", "Completa los campos y escribe un correo válido.");
            return "contacto-form";
        }

        contactos.save(contacto);
        redirectAttributes.addFlashAttribute(
                "mensajeContacto", "El contacto se guardó correctamente.");
        return "redirect:/menu";
    }

    private boolean esValido(Contacto contacto) {
        return contacto.getNombre() != null
                && !contacto.getNombre().isBlank()
                && contacto.getNombre().length() <= 100
                && contacto.getCorreo() != null
                && contacto.getCorreo().length() <= 254
                && contacto.getCorreo().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
                && contacto.getAsunto() != null
                && !contacto.getAsunto().isBlank()
                && contacto.getAsunto().length() <= 200;
    }
}
