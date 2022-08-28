---
title: FFPeg 音频
tags: FFMpeg
---
### 一些概念

音频有几个重要的参数：

- 采样率（sample_rate，单位是Hz）；
- 通道数（channels）；
- 采样格式（sample_fmt,见AVSampleFormat，其实就是采样的精度，位数越多表示越精细，当然占用的空间也会比较大）。

在ffmpeg中，音频数据的存储格式也有 `planar` 和 `packed` 之分，`planar` 表示每个通道数据单独存储，`packed` 表示通道数据交叉存储，在 `AVSampleFormat` 的类型末尾带P的就表示是 `planar` 格式的，比如双声道，用 L 表示左声道，R 表示右声道，那么：

- packed 的存储为：LRLRLRLRLRLRLRLR；
- planar 的存储为：LLLLRRRRLLLLRRRR。

### 采样格式
采样格式定义在 `libavutil/samplefmt.h` 中

```c
enum AVSampleFormat {
    AV_SAMPLE_FMT_NONE = -1,
    AV_SAMPLE_FMT_U8,          ///< unsigned 8 bits
    AV_SAMPLE_FMT_S16,         ///< signed 16 bits
    AV_SAMPLE_FMT_S32,         ///< signed 32 bits
    AV_SAMPLE_FMT_FLT,         ///< float
    AV_SAMPLE_FMT_DBL,         ///< double

    AV_SAMPLE_FMT_U8P,         ///< unsigned 8 bits, planar
    AV_SAMPLE_FMT_S16P,        ///< signed 16 bits, planar
    AV_SAMPLE_FMT_S32P,        ///< signed 32 bits, planar
    AV_SAMPLE_FMT_FLTP,        ///< float, planar
    AV_SAMPLE_FMT_DBLP,        ///< double, planar
    AV_SAMPLE_FMT_S64,         ///< signed 64 bits
    AV_SAMPLE_FMT_S64P,        ///< signed 64 bits, planar

    AV_SAMPLE_FMT_NB           ///< Number of sample formats. DO NOT USE if linking dynamically
};
```
### 通道布局（channel layout）
在 `AVCodecContext` 的结构体中有 `channel_layout` 和 `request_channel_layout` 这两个参数，它们的类型是 `uint64_t` ，表示无符号 64 位整型。

很多人对这个参数无从下手，因为不知道它表示什么，该怎么使用它，反正我是一脸懵逼！

`request_channel_layout` 表示你期待的通道布局，而 `channel_layout` 表示实际的通道布局，`channel_layout` 是由解码器设置的。

`channel_layout` 的值转换成二进制后，有多少个1就表示多少个通道。在 `channel_layout.h` 头文件中定义了有关它的一些掩码，通过这些掩码的组合就能凑成多种通道布局例如：

- AV_CH_LAYOUT_STEREO 是立体声（2通道），其通道的存放顺序为 LEFT \| RIGHT；
- AV_CH_LAYOUT_4POINT0 是4通道，其通道的存放顺序为 LEFT \| RIGHT \| FRONT-CENTER \| BACK-CENTER

有了 `channel_layout` ，我们就知道了通道的顺序，这样我们就可以随意取得我们指定的通道的数据。这里注意一点的是，sdl 不支持音频平面格式（`planar`）,因此如果用sdl播放音频必须先得转成 `packed` 格式。

以下是channel_layout.h的部分摘抄：

```c
/**
 * @defgroup channel_masks Audio channel masks
 *
 * A channel layout is a 64-bits integer with a bit set for every channel.
 * The number of bits set must be equal to the number of channels.
 * The value 0 means that the channel layout is not known.
 * @note this data structure is not powerful enough to handle channels
 * combinations that have the same channel multiple times, such as
 * dual-mono.
 *
 * @{
 */
#define AV_CH_FRONT_LEFT             0x00000001
#define AV_CH_FRONT_RIGHT            0x00000002
#define AV_CH_FRONT_CENTER           0x00000004
#define AV_CH_LOW_FREQUENCY          0x00000008
#define AV_CH_BACK_LEFT              0x00000010
#define AV_CH_BACK_RIGHT             0x00000020
#define AV_CH_FRONT_LEFT_OF_CENTER   0x00000040
#define AV_CH_FRONT_RIGHT_OF_CENTER  0x00000080
#define AV_CH_BACK_CENTER            0x00000100
#define AV_CH_SIDE_LEFT              0x00000200
#define AV_CH_SIDE_RIGHT             0x00000400
#define AV_CH_TOP_CENTER             0x00000800
#define AV_CH_TOP_FRONT_LEFT         0x00001000
#define AV_CH_TOP_FRONT_CENTER       0x00002000
#define AV_CH_TOP_FRONT_RIGHT        0x00004000
#define AV_CH_TOP_BACK_LEFT          0x00008000
#define AV_CH_TOP_BACK_CENTER        0x00010000
#define AV_CH_TOP_BACK_RIGHT         0x00020000
#define AV_CH_STEREO_LEFT            0x20000000  ///< Stereo downmix.
#define AV_CH_STEREO_RIGHT           0x40000000  ///< See AV_CH_STEREO_LEFT.
#define AV_CH_WIDE_LEFT              0x0000000080000000ULL
#define AV_CH_WIDE_RIGHT             0x0000000100000000ULL
#define AV_CH_SURROUND_DIRECT_LEFT   0x0000000200000000ULL
#define AV_CH_SURROUND_DIRECT_RIGHT  0x0000000400000000ULL
#define AV_CH_LOW_FREQUENCY_2        0x0000000800000000ULL

/** Channel mask value used for AVCodecContext.request_channel_layout
     to indicate that the user requests the channel order of the decoder output
     to be the native codec channel order. */
#define AV_CH_LAYOUT_NATIVE          0x8000000000000000ULL

/**
 * @}
 * @defgroup channel_mask_c Audio channel layouts
 * @{
 * */
#define AV_CH_LAYOUT_MONO              (AV_CH_FRONT_CENTER)
#define AV_CH_LAYOUT_STEREO            (AV_CH_FRONT_LEFT|AV_CH_FRONT_RIGHT)
#define AV_CH_LAYOUT_2POINT1           (AV_CH_LAYOUT_STEREO|AV_CH_LOW_FREQUENCY)
#define AV_CH_LAYOUT_2_1               (AV_CH_LAYOUT_STEREO|AV_CH_BACK_CENTER)
#define AV_CH_LAYOUT_SURROUND          (AV_CH_LAYOUT_STEREO|AV_CH_FRONT_CENTER)
#define AV_CH_LAYOUT_3POINT1           (AV_CH_LAYOUT_SURROUND|AV_CH_LOW_FREQUENCY)
#define AV_CH_LAYOUT_4POINT0           (AV_CH_LAYOUT_SURROUND|AV_CH_BACK_CENTER)
#define AV_CH_LAYOUT_4POINT1           (AV_CH_LAYOUT_4POINT0|AV_CH_LOW_FREQUENCY)
#define AV_CH_LAYOUT_2_2               (AV_CH_LAYOUT_STEREO|AV_CH_SIDE_LEFT|AV_CH_SIDE_RIGHT)
#define AV_CH_LAYOUT_QUAD              (AV_CH_LAYOUT_STEREO|AV_CH_BACK_LEFT|AV_CH_BACK_RIGHT)
#define AV_CH_LAYOUT_5POINT0           (AV_CH_LAYOUT_SURROUND|AV_CH_SIDE_LEFT|AV_CH_SIDE_RIGHT)
#define AV_CH_LAYOUT_5POINT1           (AV_CH_LAYOUT_5POINT0|AV_CH_LOW_FREQUENCY)
#define AV_CH_LAYOUT_5POINT0_BACK      (AV_CH_LAYOUT_SURROUND|AV_CH_BACK_LEFT|AV_CH_BACK_RIGHT)
#define AV_CH_LAYOUT_5POINT1_BACK      (AV_CH_LAYOUT_5POINT0_BACK|AV_CH_LOW_FREQUENCY)
#define AV_CH_LAYOUT_6POINT0           (AV_CH_LAYOUT_5POINT0|AV_CH_BACK_CENTER)
#define AV_CH_LAYOUT_6POINT0_FRONT     (AV_CH_LAYOUT_2_2|AV_CH_FRONT_LEFT_OF_CENTER|AV_CH_FRONT_RIGHT_OF_CENTER)
#define AV_CH_LAYOUT_HEXAGONAL         (AV_CH_LAYOUT_5POINT0_BACK|AV_CH_BACK_CENTER)
#define AV_CH_LAYOUT_6POINT1           (AV_CH_LAYOUT_5POINT1|AV_CH_BACK_CENTER)
#define AV_CH_LAYOUT_6POINT1_BACK      (AV_CH_LAYOUT_5POINT1_BACK|AV_CH_BACK_CENTER)
#define AV_CH_LAYOUT_6POINT1_FRONT     (AV_CH_LAYOUT_6POINT0_FRONT|AV_CH_LOW_FREQUENCY)
#define AV_CH_LAYOUT_7POINT0           (AV_CH_LAYOUT_5POINT0|AV_CH_BACK_LEFT|AV_CH_BACK_RIGHT)
#define AV_CH_LAYOUT_7POINT0_FRONT     (AV_CH_LAYOUT_5POINT0|AV_CH_FRONT_LEFT_OF_CENTER|AV_CH_FRONT_RIGHT_OF_CENTER)
#define AV_CH_LAYOUT_7POINT1           (AV_CH_LAYOUT_5POINT1|AV_CH_BACK_LEFT|AV_CH_BACK_RIGHT)
#define AV_CH_LAYOUT_7POINT1_WIDE      (AV_CH_LAYOUT_5POINT1|AV_CH_FRONT_LEFT_OF_CENTER|AV_CH_FRONT_RIGHT_OF_CENTER)
#define AV_CH_LAYOUT_7POINT1_WIDE_BACK (AV_CH_LAYOUT_5POINT1_BACK|AV_CH_FRONT_LEFT_OF_CENTER|AV_CH_FRONT_RIGHT_OF_CENTER)
#define AV_CH_LAYOUT_OCTAGONAL         (AV_CH_LAYOUT_5POINT0|AV_CH_BACK_LEFT|AV_CH_BACK_CENTER|AV_CH_BACK_RIGHT)
#define AV_CH_LAYOUT_HEXADECAGONAL     (AV_CH_LAYOUT_OCTAGONAL|AV_CH_WIDE_LEFT|AV_CH_WIDE_RIGHT|AV_CH_TOP_BACK_LEFT|AV_CH_TOP_BACK_RIGHT|AV_CH_TOP_BACK_CENTER|AV_CH_TOP_FRONT_CENTER|AV_CH_TOP_FRONT_LEFT|AV_CH_TOP_FRONT_RIGHT)
#define AV_CH_LAYOUT_STEREO_DOWNMIX    (AV_CH_STEREO_LEFT|AV_CH_STEREO_RIGHT)
```

### 常用api
`int64_t av_get_default_channel_layout(int nb_channels)`: 这个函数可以根据通道的个数获得默认的 `channel_layout`;

`int av_get_channel_layout_nb_channels(uint64_t channel_layout)`: 根据通道布局获得对应的通道数;

`int av_get_channel_layout_channel_index(uint64_t channel_layout,uint64_t channel)`: 获得单通道在通道布局中的下标，注意 `channel` 必须是单通道的，比如获得 `AV_CH_BACK_CENTER` 在 `AV_CH_LAYOUT_4POINT0` 中的下标，可以得到结果为 3，根据这个下标就可以取到对应通道的数据了。

### 音频解码
这里有个官方例子：[FFmpeg: decode_audio.c](http://ffmpeg.org/doxygen/trunk/decode_audio_8c-example.html)

以下是跟音频相关的常用参数:

```c
typedef struct AVCodecContext {

/* audio only */
int sample_rate; ///< samples per second
int channels;    ///< number of audio channels

/**
* audio sample format
* - encoding: Set by user.
* - decoding: Set by libavcodec.
*/
 enum AVSampleFormat sample_fmt;  ///< sample format

 /* The following data should not be initialized. */
 /**
 * Number of samples per channel in an audio frame.
 *
 * - encoding: set by libavcodec in avcodec_open2(). Each submitted frame
 *   except the last must contain exactly frame_size samples per channel.
 *   May be 0 when the codec has AV_CODEC_CAP_VARIABLE_FRAME_SIZE set, then the
 *   frame size is not restricted.
 * - decoding: may be set by some decoders to indicate constant frame size
 */
int frame_size;

/**
  * Audio cutoff bandwidth (0 means "automatic")
  * - encoding: Set by user.
  * - decoding: unused
   */
   int cutoff;
  
   /**
 * Audio channel layout.
 * - encoding: set by user.
 * - decoding: set by user, may be overwritten by libavcodec.
  */
       uint64_t channel_layout;
 /**
   * Request decoder to use this channel layout if it can (0 for default)
    * - encoding: unused
    * - decoding: Set by user.
    */
   uint64_t request_channel_layout;
    /**
    * Type of service that the audio stream conveys.
     * - encoding: Set by user.
     * - decoding: Set by libavcodec.
     */
    enum AVAudioServiceType audio_service_type;
   
   /**
    * desired sample format
   * - encoding: Not used.
   * - decoding: Set by user.
   * Decoder will decode to this format if it can.
   */
  enum AVSampleFormat request_sample_fmt;
}
```
其中 `frame_size` 的意思就是一个 `packet` 中的采样数，比如采样率是 `48000`, `frame_size = 1152`, 则表示每秒有 `48000` 次采样，而每个 `packet` 有 `1152` 次采样，因此一个 `packet` 的时间是 `1152/48000 * 1000 = 24毫秒`。`channel_layout` 是声道布局，表示多声道的个数和顺序，有了这个顺序才能顺利取到需要的数据。

### libswresample

`libswresample` 主要是用于音频的重采样和格式转换的,包含如下功能：

- 采样频率转换：对音频的采样频率进行转换的处理，例如把音频从一个高的 `44100Hz` 的采样频率转换到 `8000Hz`；从高采样频率到低采样频率的音频转换是一个有损的过程；
- 声道格式转换：对音频的声道格式进行转换的处理，例如立体声转换为单声道；当输入通道不能映射到输出流时，这个过程是有损的，因为它涉及不同的增益因素和混合；
- 采样格式转换：对音频的样本格式进行转换的处理，例如把 `s16（AV_SAMPLE_FMT_S16）` 的 PCM 数据转换为 `s8格式` 或者 `f32` 的 PCM 数据；此外提供了 `Packed` 和 `Planar` 包装格式之间相互转换的功能；

当音频的采样率与播放器的采样率不一致时，那么想在播放器正常播放，就需要对音频进行重采样，否则可能会出现音频变速的问题。

### 音频转换
音频转换一般就是指 `planar` 和 `packed` 的互转，或者声道之间的转换。当我们解码后的音频数据是 `planar` 的，而我们的播放器却只支持 `packed` 的，那么我们就需要将 `planar` 转为 `packed`，例如是双声道的话就是要将原本为 `LLLLRRRR` 的数据变为 `LRLRLRLR`，知道这个原理后，其实两个for循环就能搞定这次转换，如下：

```c
 data_size = av_get_bytes_per_sample(dec_ctx->sample_fmt);
 for (i = 0; i < frame->nb_samples; i++)
     for (ch = 0; ch < dec_ctx->channels; ch++)
         fwrite(frame->data[ch] + data_size*i, 1, data_size, outfile);
```
以上只是为了更好的理解转换过程，实际上ffmpeg已经提供了相关的接口来帮助我们转换，如非特别需求，建议还是使用ffmpeg提供的转换接口，在ffmpeg中，转换主要包含3个步骤：

- 实例化 `SwrContext`；
- 计算转换后的 `sample` 个数；
- 调用 `swr_convert` 进行转换；

转换的参考代码如下，主要的 api 都在 `libswresample/swresample.h`：

```c
uint8_t **input;
int in_samples;

//第一种方式创建SwrContext
//SwrContext *swr = swr_alloc();
// av_opt_set_channel_layout(swr, "in_channel_layout",  AV_CH_LAYOUT_5POINT1, 0);
// av_opt_set_channel_layout(swr, "out_channel_layout", AV_CH_LAYOUT_STEREO,  0);
// av_opt_set_int(swr, "in_sample_rate",     48000,                0);
// av_opt_set_int(swr, "out_sample_rate",    44100,                0);
// av_opt_set_sample_fmt(swr, "in_sample_fmt",  AV_SAMPLE_FMT_FLTP, 0);
// av_opt_set_sample_fmt(swr, "out_sample_fmt", AV_SAMPLE_FMT_S32,  0);

//第二种方式创建SwrContext，以下代码作用等同于上面的
SwrContext *swr = swr_alloc_set_opts(NULL,  // we're allocating a new context
                         AV_CH_LAYOUT_STEREO,  // out_ch_layout
                         AV_SAMPLE_FMT_S32,    // out_sample_fmt
                         44100,                // out_sample_rate
                         AV_CH_LAYOUT_5POINT1, // in_ch_layout
                         AV_SAMPLE_FMT_FLTP,   // in_sample_fmt
                         48000,                // in_sample_rate
                         0,                    // log_offset
                         NULL);                // log_ctx


//在得到SwrContext后就要进行初始化 ，如果SwrContext的参数有任何变化，则必须再次调用以下初始化函数
swr_init(swr)；

//这里演示修改了第三个参数为AV_SAMPLE_FMT_S16，则需要再次调用swr_init
swr = swr_alloc_set_opts(swr,  
                         AV_CH_LAYOUT_STEREO,  // out_ch_layout
                         AV_SAMPLE_FMT_S16,    // out_sample_fmt
                         44100,                // out_sample_rate
                         AV_CH_LAYOUT_5POINT1, // in_ch_layout
                         AV_SAMPLE_FMT_FLTP,   // in_sample_fmt
                         48000,                // in_sample_rate
                         0,                    // log_offset
                         NULL);                // log_ctx
                         
swr_init(swr)；//再次调用

//计算转换后的采样数samples,计算公式为 in_samples*out_sample_rate=out_samples*in_sample_rate
//该运算在数学上等价于a * b / c，最后一个参数可以支持多种取舍
int out_samples = av_rescale_rnd(
    swr_get_delay(swr /*获取下一个输入样本相对于下一个输出样本将经历的延迟*/,  48000/*输入采样率 */) + in_samples, 
    44100, //输出采样率
    48000, //输入采样率
    AV_ROUND_UP);//表示向上取整，如3/2=2

//根据转换后的音频参数分配一块缓冲来存储数据
uint8_t *  output[8];//用于存储转换后的数据
//分配一个样本缓冲区，并相应地填充数据指针和行大小
//可以使用av_freep（＆output [0]）释放分配的样本缓冲区
  av_samples_alloc(&output,             //[out]
    				 NULL,              //[out]
    				 2,                 //通道数
    				 out_samples,       //采样数
                     AV_SAMPLE_FMT_S16, //采样格式
                     0);                //对齐，0--默认，1--不对齐

 out_samples = swr_convert(swr, 
   						      &output, //转换后的数据
   						      out_samples,
                              input, //要转换的数据
                              in_samples);

if(swr_get_out_samples(swr,0)>0){//表示有缓冲数据
//通过设置in和in_count为0将缓存中的全部处理完毕，这通常是最后一步，如果没有这步，则可能最后的音频数据会存在缓冲中没有全部转换出来
  out_samples = swr_convert(swr, 
   						      &output, 
   						      out_samples,
                              NULL, 
                              0);
}
                             
swr_free(&swr)；//最后释放

```
- swr_convert: 在转换过程中如果输入采样数大于输出采样数，那么超出的部分会被Swresample缓存起来，因此输出采样数这个参数要根据输入采样数和已经存在的缓存进行计算，否则可能会导致缓存的采样数越来越多，内存一直在上涨；当in和in_count都为0时，就表示要把缓存中数据都输出出来了;
- swr_get_out_samples: 函数的意思是获得下一个输出样本缓冲的数量，相同的输入返回值并不是一样的，这取决于内部的缓存采样数的多少；我们知道，swr_convert在调用后，如果输入的采样数比输出的采样数大，那么Swresample便会对超出的那部分进行缓存，如果输入一直比输出大，那么内存就会一直上涨，为此，我们需要swr_get_out_samples这个函数让我们得知输出应该为多大才能把缓冲里的数据也带走，比如Swresample里已经有10个采样数的缓存了，此时输入如果为100个采样数，那么我们希望输出为110（这是在不改变采样率的情况下），这样就能把所有数据都输出，缓存也清空了，就不会引起内存上涨，那么怎么得到这个110呢，通过swr_get_out_samples（swr,100）=110；如果swr_get_out_samples（swr,0）就表示获得Swresample已经缓存的采样数；最后总结一下，swr_get_out_samples就是根据你的输入采样数，得到应该取走的输出采样数，如果输入采样数为0，那么就能得到已经缓存的采样数，得到这个输出采样数后，我们才知道应该通过av_samples_alloc为输出样本分配多大的缓存空间;
- swr_get_delay: FFMPEG 转码的过程中 , 可能没有一次性将一帧数据处理完毕 , 如输入了 20 个数据 , 一般情况下 20 个数据都能处理完毕 , 有时还会出现只处理了 19 个 , 剩余的 1 个数据就积压在了缓冲区中的情况 , 如果这种积压在缓冲区中的数据过大 , 会造成很大的音频延迟 , 甚至内存崩溃；所以每次音频处理时 , 都尝试将上一次积压的音频采样数据加入到本次处理的数据中 , 防止出现音频延迟的情况;


当然可以参考官方的 [Demo](http://ffmpeg.org/doxygen/trunk/resampling_audio_8c-example.html)

### 相关计算

`AVFrame.nb_samples`（ `AVFrame.nb_samples` 和 `AVCodecContext.frame_size` 实际上是同一个东西，他们相等）表示的是每帧每个通道的采样数，`aac` 一般是 `1024（LC）`或 `2048（HE）`，`mp3` 一般是 `1152`。

A HE-AAC v1 or v2 audio frame contains 2048 PCM samples per channel
(there is also one mode with 1920 samples per channel but this is
only for special purposes such as DAB+ digital radio). These
bits/frame figures are average figures where each AAC frame generally
has a different size in bytes. To calculate the same for AAC-LC just
use 1024 instead of 2048 PCM samples per frame and channel. For
AAC-LD/ELD it is either 480 or 512 PCM samples per frame and channel.
{:.info}

我们知道从解码器解码出一帧，如果是视频的话，那这一帧通常就是一个画面的数据，而对于音频来说，这一帧大概只包含几十毫秒长的音频数据，那么怎么知道这一帧究竟是表示多长的音频呢？

首先我们要清楚 `AVFrame.nb_samples` 等于 `AVCodecContext.farme_size`，它表示的意思就是每帧每个通道的采样数，有了它，我们就可以计算出一帧包含的音频时长，因为音频都有一个采样率，它表示一秒采样多少次，现在我们知道了采样数，那么一除就可以知道时长了，如下：

一帧时长(ms) = 一帧采样数 / 采样率 * 1000
{:.info}

在 ffmpeg 中一帧采样数就是 `AVFrame.nb_samples` 或者 `AVCodecContext.farme_size`，而采样率就是 `AVCodecContext.sample_rate`，比如采样率是 `48000`，而`frame_size=1152`，那么就可以得出这一帧的音频时长为`1152/48000*1000 = 24毫秒`。

此时你已经知道了一帧的音频时长，假如你还想知道这一帧的数据大小那该怎么计算呢？

我们知道，每次采样后都需要保存这个采样的数据，那么用多大来保存它呢，这就看 `AVSampleFormat` 了，比如 `AV_SAMPLE_FMT_S16` 就表示用 16 位即 2 个字节来保存一个采样数据，当然位数越多，能保存的数据就会更多，音质肯定就会越好。

我们知道 `AV_SAMPLE_FMT_S16` 是占 2 个字节，`AV_SAMPLE_FMT_S32` 是占 4 个字节，在 ffmpeg 中提供了一个函数 `av_get_bytes_per_sample()`，它可以帮我们计算出 `AVSampleFormat` 究竟是几个字节，比如：

- av_get_bytes_per_sample(AV_SAMPLE_FMT_S16) = 2
- av_get_bytes_per_sample(AV_SAMPLEAV_SAMPLE_FMT_S32) = 4

有了上面的分析，我们就可以得出： `一帧的数据大小 = 采样数 * 存储采样的位数 * 声道数`

这一帧的数据都保存在 `AVFrame.data` 里，假如是双声道，`planar` 格式，那么我们想取出左声道的数据的话，那得先知道左声道的数据放在 `AVFrame.data` 这个数组的哪个下标，在ffmpeg中 `av_get_channel_layout_channel_index()` 这个函数就能帮助我们得到下标，然后我们就可以愉快取出数据了。

根据上面的分析，我们还可以轻松计算出 1 秒能包含多少帧音频，假如我们知道了采样率，采样格式，一帧的采样数，那么我们就可以计算出1秒包含的音频帧数。比如采样率是 `88200`，采样格式是 `AV_SAMPLE_FMT_S16` (这么写太长了，这里用s16代替下)，一帧的采样数是 `1024`，那么一帧数据量：

1024 * 2 * av_get_bytes_per_sample(s16) = 4096 个字节
{:.info}

一秒可以编码的帧数：

88200/(1024 * 2 * av_get_bytes_per_sample(s16)) = 21.5 帧数据
{:.info}

### 参考

[计算音频延迟](https://blog.csdn.net/shulianghan/article/details/104871223/?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-4--blog-83818443.pc_relevant_default&spm=1001.2101.3001.4242.3&utm_relevant_index=7)
[FFMpeg 音频重采样 Demo](http://ffmpeg.org/doxygen/trunk/resampling_audio_8c-example.html)
[ffmpeg音频存储格式packed和planar](https://blog.csdn.net/qq_18998145/article/details/97394595)
[FFmpeg关于nb_smples,frame_size以及profile的解释](https://blog.csdn.net/eydwyz/article/details/78748241)