package pe.edu.unprg.javaee.inventariolibros.service;

import pe.edu.unprg.javaee.inventariolibros.model.Publisher;

import java.util.List;

public interface PublisherService {

    boolean createPublisher(Publisher publisher);
    boolean editPublisher(Publisher publisher);
    Publisher findByPublisherId(int publisherId);
    List<Publisher> findAll();
    boolean disableByPublisherId(int publisherId);

}