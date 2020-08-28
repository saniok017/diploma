package org.arhor.diploma.data.file

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.arhor.diploma.core.Identifiable
import org.arhor.diploma.util.createLogger
import org.springframework.core.io.ResourceLoader
import java.io.Serializable
import javax.annotation.PostConstruct
import kotlin.streams.toList

abstract class DataProviderImplDefault<
        T : Identifiable<K>,
        D : Identifiable<K>,
        K : Serializable
        >(
    private val resourceLoader: ResourceLoader
) : DataProvider<T, D, K> {

    companion object {
        private val log = createLogger<DataProviderImplDefault<*,*,*>>()
    }

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    protected abstract val resourcePath: String
    protected abstract val resourceType: Class<Array<D>>

    protected var data: Set<D> = emptySet()

    protected abstract fun shrinkData(details: D): T

    override fun getOne(id: K): T {
        return getDetails(id).let(::shrinkData)
    }

    override fun getList(): List<T> {
        return getDetailsList().map(::shrinkData)
    }

    override fun getList(page: Int, size: Int): List<T> {
        return getDetailsList(page, size).map(::shrinkData)
    }

    override fun getDetails(id: K): D {
        return data.first { it.getId() == id }
    }

    override fun getDetailsList(): List<D> {
        return data.toList()
    }

    override fun getDetailsList(page: Int, size: Int): List<D> {
        val offset = ((page - 1) * size).toLong()
        val amount = size.toLong()

        return data.takeIf { it.isNotEmpty() }
            ?.stream()
            ?.skip(offset)
            ?.limit(amount)
            ?.toList()
            ?: emptyList()
    }

    @PostConstruct
    override fun reload() {
        try {
            data = loadData()
        } catch (e: Throwable) {
            log.error("Attempt to reload data-file was failed", e)
        }
    }

    private fun loadData(): Set<D> {
        return resourceLoader.getResource(resourcePath).takeIf { it.isReadable }
            ?.inputStream
            ?.bufferedReader()
            ?.use { reader -> objectMapper.createParser(reader).readValueAs(resourceType) }
            ?.let { values -> setOf(*values) }
            ?: emptySet()
    }
}