package com.roswer.data.databese.exceptions

/**
 * Exception for database errors
 */
class DatabaseException(override val message: String? = null) : Exception(message) {}