package com.jz.service;

import com.jz.domain.*;

import java.io.IOException;
import java.util.List;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/6
 */
public interface MovieService {

    public List<Model> findImax() throws Exception;

    public List<Album> findAllAlbum() throws IOException;

    public Search_data search(String keyWord) throws IOException;

    public Model getModelInfo(String url) throws IOException;

    String urlFormat(String url) throws IOException;

    List<Category> getCategory();

    ModelAllSet getAllTxTvByUrl(String url) throws IOException;
}
