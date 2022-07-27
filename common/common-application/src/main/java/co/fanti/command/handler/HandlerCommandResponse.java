package co.fanti.command.handler;

import org.springframework.transaction.annotation.Transactional;

public interface HandlerCommandResponse<C, R> {

    @Transactional
    R run(C command);
}
