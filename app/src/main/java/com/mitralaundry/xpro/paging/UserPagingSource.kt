package com.mitralaundry.xpro.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mitralaundry.xpro.data.model.response.UserResponse
import com.mitralaundry.xpro.data.network.JituApi
import java.lang.Exception


class UserPagingResource (private val api : JituApi) : PagingSource<Int, UserResponse>() {

    override fun getRefreshKey(state: PagingState<Int, UserResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse> {
        return try {
            val position = params.key ?: 1
            val response = api.getUser()

            return LoadResult.Page(
                data = response,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == 50) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

