/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entities.TypeService;
import java.util.List;

/**
 *
 * @author Yasmine Rajhi
 */
public interface InterfaceTypeService {
    public void addTypeServices(TypeService TS);
    public void updateTypeServices(TypeService TS);
    public void deleteTypesServices(TypeService TS);
    public List<TypeService> showTypesServices();  
}
