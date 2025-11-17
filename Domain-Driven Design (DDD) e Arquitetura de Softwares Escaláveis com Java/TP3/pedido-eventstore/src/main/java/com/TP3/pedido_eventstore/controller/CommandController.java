package com.TP3.pedido_eventstore.controller;

import com.TP3.pedido_eventstore.commands.CriarPedidoCommand;
import com.TP3.pedido_eventstore.events.PedidoCriadoEvent;
import com.TP3.pedido_eventstore.service.CommandHandlerService;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável por receber os comandos e acionar o CommandHandlerService.
 * Representa o "Command Side" do padrão CQRS.
 */
@RestController
@RequestMapping("/api/commands")
public class CommandController {

    private final CommandHandlerService commandHandlerService;

    public CommandController(CommandHandlerService commandHandlerService) {
        this.commandHandlerService = commandHandlerService;
    }

    /**
     * Endpoint responsável por executar o comando CriarPedidoCommand.
     * Exemplo de uso: POST /api/commands/criar-pedido
     */
    @PostMapping("/criar-pedido")
    public PedidoCriadoEvent criarPedido(@RequestBody CriarPedidoCommand command) {
        return commandHandlerService.handle(command);
    }
}
