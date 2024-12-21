package com.revature.library.mocks;

import com.revature.library.Models.BookLog;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class DaoMocker<T, ID> implements JpaRepository<T, ID> {
    final List<T> items = new ArrayList<>();
    
    abstract ID getId(T entry);

    abstract T addId(T entry, int index);

    @Override
    public final <S extends T> S save(S entity) {
        var indexOf = items.indexOf(entity);
        if (indexOf == -1) {
            var result = addId(entity, items.size()+1);

            items.add(result);

            return (S)result;
        } else {
            var result = addId(entity, indexOf+1);

            items.set(indexOf, result);

            return (S)result;
        }
    }

    @Override
    public final Optional<T> findById(ID id) {
        if (id == (Object)0){
            throw new RuntimeException();
        }
        
        return items.stream().filter(x->getId(x).equals(id)).findFirst();
    }

    @Override
    public final boolean existsById(ID id) {
        if (id == (Object)0){
            throw new RuntimeException();
        }
        
        return this.findById(id).isPresent();
    }

    @Override
    public final List<T> findAll() {
        return this.items;
    }

    @Override
    public final void deleteById(ID id) {
        if (id == (Object)0){
            throw new RuntimeException();
        }
        
        var iter = this.items.iterator();
        
        while (iter.hasNext()){
            if (getId(iter.next()).equals(id)){
                iter.remove();
                return;
            }
        }
    }

    @Override
    public final void delete(T entity) {
        this.deleteById(getId(entity));
    }

    @Override
    public final void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final <S extends T> S saveAndFlush(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final <S extends T> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void deleteAllInBatch(Iterable<T> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void deleteAllByIdInBatch(Iterable<ID> ids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void deleteAllInBatch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final T getOne(ID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final T getById(ID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final T getReferenceById(ID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final <S extends T> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public final <S extends T> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final <S extends T> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final <S extends T> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final <S extends T> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final List<T> findAllById(Iterable<ID> ids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final long count() {
        return 0;
    }

    @Override
    public final void deleteAllById(Iterable<? extends ID> ids) {

    }

    @Override
    public final void deleteAll(Iterable<? extends T> entities) {

    }

    @Override
    public final void deleteAll() {

    }

    @Override
    public final List<T> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Page<T> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

//    abstract ID getFromEntry(T entry);
//    
//    abstract T fill(T entry, int index);
    
    
}
