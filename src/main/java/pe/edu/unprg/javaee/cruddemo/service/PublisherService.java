package pe.edu.unprg.javaee.cruddemo.service;

import pe.edu.unprg.javaee.cruddemo.model.Publisher;

import java.util.List;

public interface PublisherService {

    boolean createPublisher(Publisher publisher);
    boolean editPublisher(Publisher publisher);
    Publisher findByPublisherId(int publisherId);
    List<Publisher> findAll();
    boolean disableByPublisherId(int publisherId);

}