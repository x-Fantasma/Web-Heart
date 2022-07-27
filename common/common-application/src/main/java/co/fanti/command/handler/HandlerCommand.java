package co.fanti.command.handler;


import org.springframework.transaction.annotation.Transactional;

public interface HandlerCommand<C> {

    @Transactional
    void run(C command);
}
