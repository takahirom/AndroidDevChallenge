package com.github.takahirom.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.takahirom.androiddevchallenge.ui.theme.AndroidDevChallengeTheme

sealed class Screen {
    object List : Screen()
    class Detail(val index: Int) : Screen()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidDevChallengeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val screenState = remember { mutableStateOf<Screen>(Screen.List) }
                    when (val screen = screenState.value) {
                        Screen.List ->
                            PuppyList {
                                screenState.value = Screen.Detail(it)
                            }
                        is Screen.Detail -> {
                            PuppyDetail(
                                screen.index
                            )
                        }

                    }

                }
            }
        }
    }


}


class Puppy(val name: String, val age: Int)

val puppis = listOf(
    Puppy("Abby", 10),
    Puppy("Abe", 5),
    Puppy("Addie", 5)
)

@Composable
fun PuppyList(onClick: (Int) -> Unit) {
    LazyColumn {
        items(puppis) { puppy ->
            Text(
                modifier = Modifier.clickable { onClick(puppis.indexOf(puppy)) },
                text = puppy.name
            )
        }
    }
}

@Composable
private fun PuppyDetail(index: Int) {
    val puppy = puppis[index]
    Column {
        Text("name:" + puppy.name)
        Text("age:" + puppy.age.toString())
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewList() {
    AndroidDevChallengeTheme {
        PuppyList {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetail() {
    AndroidDevChallengeTheme {
        PuppyDetail(index = 0)
    }
}