package com.tfandkusu.template.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class LocalFavorite(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val githubRepoId: Long
)
