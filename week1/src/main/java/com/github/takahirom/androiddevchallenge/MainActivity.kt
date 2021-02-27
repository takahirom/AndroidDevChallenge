package com.github.takahirom.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.github.takahirom.androiddevchallenge.ui.theme.AndroidDevChallengeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidDevChallengeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "list") {
                        composable(
                            route = "list",
                        ) {
                            PuppyList {
                                navController.navigate("detail/$it")
                            }
                        }
                        composable(
                            route = "detail/{index}",
                            arguments = listOf(
                                navArgument("index") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val index = backStackEntry
                                .arguments?.getString("index")
                                ?.toInt() ?: throw IllegalArgumentException()
                            PuppyDetail(
                                index
                            )
                        }
                    }
                }
            }
        }
    }


}


class Puppy(val image: String, val name: String, val age: Int)

val puppis = listOf(
    Puppy("🐕", "Abby", 10),
    Puppy("🐶", "Abe", 5),
    Puppy("🐩", "Addie", 5)
)

@Composable
fun PuppyList(onClick: (Int) -> Unit) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(puppis) { puppy ->
            Card(
                modifier = Modifier
                    .clickable { onClick(puppis.indexOf(puppy)) }
                    .padding(4.dp)
                    .fillMaxWidth(),
                elevation = 4.dp
            ) {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(30.dp),
                        text = puppy.image,
                        style = TextStyle(fontSize = 24.sp)
                    )
                    Text(
                        modifier = Modifier,
                        text = puppy.name,
                        style = MaterialTheme.typography.h4
                    )
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
private fun PuppyDetail(index: Int) {
    val puppy = puppis[index]
    Column {
        Text(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            text = puppy.image,
            style = TextStyle(fontSize = 80.sp)
        )
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "name:" + puppy.name
        )
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "age:" + puppy.age.toString()
        )
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