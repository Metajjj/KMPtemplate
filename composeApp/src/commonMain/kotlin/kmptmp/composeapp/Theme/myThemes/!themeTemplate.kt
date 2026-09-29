package kmptmp.composeapp.Theme.myThemes

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


@Serializable
data class myColors(
   @Serializable(with = ColorSerial::class)  val Background: Color,
   @Serializable(with = ColorSerial::class)  val Title: Color,
   @Serializable(with = ColorSerial::class)  val Interactable: Color,
   @Serializable(with = ColorSerial::class)  val Text: Color,
   @Serializable(with = ColorSerial::class)  val EditTextBorder: Color,
   @Serializable(with = ColorSerial::class)  val FragBackground: Color,
   @Serializable(with = ColorSerial::class)  val DelHighlight: Color,
   @Serializable(with = ColorSerial::class)  val NoteTextBorder: Color,
) {
   //needed for seri to work with color
   object ColorSerial : KSerializer<Color>{
      override val descriptor: SerialDescriptor =
         PrimitiveSerialDescriptor( "androidx.compose.ui.graphics.Color",PrimitiveKind.STRING )

      override fun serialize( encoder: Encoder, value: Color ) = encoder.encodeString(value.value.toString())
      override fun deserialize( decoder: Decoder ): Color = Color(decoder.decodeString().toULong())
   }
}
