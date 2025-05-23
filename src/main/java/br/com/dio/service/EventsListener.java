package br.com.dio.service;

import br.com.dio.service.enums.EventEnum;

public interface EventsListener {
    void update(final EventEnum eventType);
}
