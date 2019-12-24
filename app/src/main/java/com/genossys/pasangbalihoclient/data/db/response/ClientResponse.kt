package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.Client

data class ClientResponse(
    val respon: String?,
    val message: String?,
    val client: Client?
)