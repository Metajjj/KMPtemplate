package kmptmp.composeapp.UI

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import kmptmp.composeapp.Theme.ThemeItfc
import kmptmp.composeapp.Theme.ThemeItfc.Companion.cTheme
import kmptmp.composeapp.generated.resources.Res
import kmptmp.composeapp.generated.resources.remind_ful_icon2
import org.jetbrains.compose.resources.painterResource

@Preview @Composable private fun Prev(){Prev{CHome()}}

@Composable fun CHome(){
    Surface(
        color = cTheme.Background,
        contentColor = cTheme.Title,
        content = {
            ConstraintLayout(
                Modifier.fillMaxSize()
            ) {
                val (CLimage, CLtext) = createRefs();

                Image(
                    painterResource( Res.drawable.remind_ful_icon2 )
                    ,
                    null
                    ,
                    Modifier
                        .fillMaxSize()
                        .constrainAs(CLimage,{
                            absoluteLeft.linkTo(parent.absoluteLeft, 5.dp)
                            absoluteRight.linkTo(parent.absoluteRight, 5.dp)
                            top.linkTo(parent.top, 5.dp)
                            bottom.linkTo(parent.bottom, 5.dp)
                        })
                )
                Text(
                    "My crummy template",
                    Modifier.fillMaxWidth()
                        .padding(2.dp)
                        .constrainAs(CLtext,{
                            absoluteLeft.linkTo(parent.absoluteLeft, 5.dp)
                            absoluteRight.linkTo(parent.absoluteRight, 5.dp)
                            top.linkTo(parent.top, 5.dp)
                            bottom.linkTo(parent.bottom, 5.dp)

                            verticalBias = 0.1f
                        })
                        .clickable{
                            ThemeItfc.themeUpdate()
                        }
                    ,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        textAlign = TextAlign.Center,
                    )
                )
            }

            //Example password input
            val ph = "Homework Folder "
            var input by remember{mutableStateOf("")}
            TextField(
                value=input,
                onValueChange ={
                    input=it;
                    println("wahhh : $input")
                },
                modifier = Modifier
                    .wrapContentSize()
                ,
                visualTransformation =
                    VisualTransformation {
                        TransformedText(
                            AnnotatedString(
                                ph
                                    .repeat(it.length)
                            ),
                            object : OffsetMapping {
                                override fun originalToTransformed(offset: Int): Int =
                                    offset * ph.length

                                override fun transformedToOriginal(offset: Int): Int =
                                    offset / ph.length

                            }
                        )
                    }
                ,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = cTheme.Interactable,
                    focusedContainerColor = cTheme.Interactable,
                    focusedTextColor = cTheme.NoteTextBorder,
                    unfocusedTextColor = cTheme.NoteTextBorder
                ),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                placeholder = {
                    Text(
                        text="Enter your NHS number",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color= cTheme.DelHighlight,
                        )
                    )},
            )
        }
    )
}