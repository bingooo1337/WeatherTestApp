package com.kamyshanov.volodymyr.weathertestapp.data.mapper

interface Mapper<Response, Database, Domain> {
  fun mapResponseToDomain(response: Response): Domain

  fun mapResponseToDatabase(response: Response): Database

  fun mapDatabaseToDomain(database: Database): Domain
}