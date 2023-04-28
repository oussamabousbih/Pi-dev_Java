/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entities.Service;
import java.util.List;

/**
 *
 * @author Yasmine Rajhi
 */
public interface InterfaceService {
    public void addServices(Service S);
    public void updateServices(Service S);
    public void deleteServices(Service S);
    public List<Service> showServices();
}
