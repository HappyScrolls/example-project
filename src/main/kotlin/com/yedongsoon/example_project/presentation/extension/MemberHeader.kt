package com.yedongsoon.example_project.presentation.extension

import com.fasterxml.jackson.annotation.JsonProperty


data class MemberHeader
(
        @JsonProperty("no")
        val no: Int,
        @JsonProperty("name")
        val name: String,
        @JsonProperty("account")
        val account: String,
)
