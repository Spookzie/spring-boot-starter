package com.spookzie.database.mappers;


/*  Mapper Interface for Entity to DTO mapping functionality & vice versa   */
public interface Mapper<A, B>
{
    B mapTo(A a);
    A mapFrom(B b);
}