/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Article;
import java.util.List;

/**
 *
 * @author mazee
 */
public interface InterfaceArticle {
    public void ajouterArticle(Article A);
    public void modifierArticle(Article A);
    public void supprimerArticle(Article A);
    public List<Article> afficherArticle();
    public Article afficherArticleDetail(int articleId);
}
