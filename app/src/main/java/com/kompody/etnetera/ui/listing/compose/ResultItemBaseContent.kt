package com.kompody.etnetera.ui.listing.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kompody.etnetera.R
import com.kompody.etnetera.ui.theme.IndicatorBackground
import com.kompody.etnetera.utils.extensions.empty

@Composable
fun ResultItemBaseContent(
    sportName: String = String.empty,
    place: String = String.empty,
    duration: String = String.empty,
    date: String = String.empty,
    background: Color = Color.White,
    indicatorText: String = String.empty,
    indicatorColor: Color = Color.Black
) {
    ConstraintLayout(
        modifier = Modifier
            .background(background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
    ) {
        val (values, indicator, datevalue) = createRefs()
        Row(
            modifier = Modifier
                .constrainAs(values) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.listing_item_name),
                    modifier = Modifier.height(24.dp)
                )
                Text(
                    text = stringResource(id = R.string.listing_item_place),
                    modifier = Modifier.height(24.dp)
                )
                Text(
                    text = stringResource(id = R.string.listing_item_duration),
                    modifier = Modifier.height(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = sportName,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.height(24.dp)
                )
                Text(
                    text = place,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.height(24.dp)
                )
                Text(
                    text = duration,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.height(24.dp)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(20.dp)
                .background(
                    color = Color.IndicatorBackground,
                    shape = MaterialTheme.shapes.large
                )
                .constrainAs(indicator) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = indicatorText,
                color = indicatorColor,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = date,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(datevalue) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}