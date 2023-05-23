@file:OptIn(ExperimentalMaterial3Api::class)

package com.mongodb.dublinmug_kmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mongodb.dublinmug_kmm.QueryInfo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Container()
            }
        }
    }
}


@Preview
@Composable
fun Container() {
    val viewModel = viewModel<MainViewModel>()
    val onEditRequest = remember { mutableStateOf(QueryInfo()) }

    val onEditClick = { query: QueryInfo ->
        onEditRequest.value = query
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Ask me?",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primaryContainer),
                navigationIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_menu_24),
                        contentDescription = ""
                    )
                }
            )
        },
        containerColor = (Color(0xffF9F9F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_realm_logo),
                    contentScale = ContentScale.Fit,
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .width(200.dp)
                        .defaultMinSize(minHeight = 200.dp)
                        .padding(bottom = 20.dp),
                )
            }

            AddQuery(viewModel, onEditRequest)

            Text(
                "Queries",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            QueriesList(viewModel, onEditClick)
        }
    }
}


@Composable
fun AddQuery(viewModel: MainViewModel, query: MutableState<QueryInfo>) {

    val inputState = LocalTextInputService.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text(text = "Enter your query here") },
        trailingIcon = {
            Icon(
                painterResource(id = R.drawable.ic_baseline_send_24),
                contentDescription = "",
                modifier = Modifier.clickable {
                    viewModel.onSendClick(query.value)
                    query.value = QueryInfo()
                    inputState?.hideSoftwareKeyboard()
                })
        },
        value = query.value.queries,
        onValueChange = {
            query.value = QueryInfo().apply {
                _id = query.value._id
                queries = it
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onDone = { inputState?.hideSoftwareKeyboard() })
    )
}

@Composable
fun QueriesList(viewModel: MainViewModel, onEditClick: (QueryInfo) -> Unit) {

    val queries = viewModel.queries.observeAsState(initial = emptyList()).value

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(8.dp),
        content = {
            items(items = queries, itemContent = { item: QueryInfo ->
                QueryItem(query = item, onEditClick)
            })
        })
}

@Preview
@Composable
fun QueryPreview() {
    QueryItem(query = QueryInfo().apply {
        queries = "Sample text"
    }, onEditClick = {})
}

@Composable
fun QueryItem(query: QueryInfo, onEditClick: (QueryInfo) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(text = query.queries, modifier = Modifier.fillMaxWidth(0.95f))

        IconButton(onClick = {
            onEditClick(query)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_edit_24),
                contentDescription = "Edit"
            )
        }
    }
}







