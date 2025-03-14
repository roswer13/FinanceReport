package com.example.financereport.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.domain.module.categories.model.Category

@Composable
fun CategoryItem(category: Category, onClick: (Category) -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .clickable { onClick(category) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(category.color.toColorInt()))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Image(
                painter = painterResource(id = category.icon),
                contentDescription = category.name,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 4.dp)
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Text(
                text = category.name, modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
    CategoryItem(category = Category.buildFake(), onClick = {})
}