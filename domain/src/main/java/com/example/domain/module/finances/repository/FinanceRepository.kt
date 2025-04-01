package com.example.domain.module.finances.repository

import com.example.domain.module.finances.models.Finance

/**
 * Interface representing a repository for managing finance data.
 */
interface FinanceRepository {

    /**
     * Retrieves a list of all finances.
     *
     * @return A list of [Finance] objects.
     */
    suspend fun getFinancesList(): List<Finance>

    /**
     * Retrieves a list of finances for a specific month and year.
     *
     * @param month The month for which to retrieve finances.
     * @param year The year for which to retrieve finances.
     * @return A list of [Finance] objects for the specified month and year.
     */
    suspend fun getFinancesListByMonthYear(month: Int, year: Int): List<Finance>

    /**
     * Retrieves a finance by its ID.
     *
     * @param id The ID of the finance to retrieve.
     * @return The [Finance] object with the specified ID.
     */
    suspend fun getFinanceById(id: Int): Finance

    /**
     * Updates a finance object.
     *
     * @param finance the [Finance] object to update.
     * return `true` if the finance was successfully updated, `false` otherwise.
     */
    suspend fun updateFinance(finance: Finance): Boolean

    /**
     * Saves a finance object.
     *
     * @param finance The [Finance] object to save.
     * @return `true` if the finance was successfully saved, `false` otherwise.
     */
    suspend fun saveFinance(finance: Finance): Boolean

    /**
     * Deletes a finance object.
     *
     * @param finance The [Finance] object to delete.
     * @return `true` if the finance was successfully deleted, `false` otherwise.
     */
    suspend fun deleteFinance(finance: Finance): Boolean
}