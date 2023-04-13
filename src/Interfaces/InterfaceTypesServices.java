/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entities.TypesServices;
import java.util.List;

/**
 *
 * @author Yasmine Rajhi
 */
public interface InterfaceTypesServices {
    public void addTypeServices(TypesServices TS);
    public void updateTypeServices(TypesServices TS);
    public void deleteTypesServices(TypesServices TS);
    public List<TypesServices> showTypesServices();
}
