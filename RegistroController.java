package elisonribeiro.provaApi.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/registro")
@RestController
public class RegistroController {
    public final RegistroService registroService;

    @Autowired
    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @ApiOperation(value = "Adicionar novo registro.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registro adicionado"),
            @ApiResponse(code = 403, message = "Você não tem permissão"),
            @ApiResponse(code = 500, message = "Exceção gerada"),
    })
    @PostMapping
    public ResponseEntity<RespostaRegistroDTO> salvar(@RequestBody RegistroDTO dto) {
        Registro registro = dto.transformaEmObjeto();
        registro = registroService.salvar(registro);
        return new ResponseEntity<>(RespostaRegistroDTO.transformaEmDTO(registro), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RegistroDTO> mostraRegistro(@PathVariable(value = "id") String id) {
        Registro registro = registroService.listaUm(id);
        return new ResponseEntity<>(RegistroDTO.transformaRegistro(registro), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<RegistroDTO>> listarTodos() {
        List<Registro> usuariosList = registroService.listar();
        List<RegistroDTO> registroDTOList = new ArrayList();
        usuariosList.forEach(registro -> {
            registroDTOList.add(RegistroDTO.transformaRegistro(registro));
        });
        return new ResponseEntity<>(registroDTOList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void deletaUm(@PathVariable(value = "id") Long id) {
        registroService.deletaUsuario(id);
    }

    @PutMapping(value = "/{id}")
    public Registro editaRegistro(@RequestBody Registro registro) {
        return registroService.salvar(registro);
    }
}
