package io.github.yukikaze.insert_chatgpt.dto.completions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.yukikaze.insert_chatgpt.config.DoubleContextualSerializer;
import io.github.yukikaze.insert_chatgpt.config.Precision;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
public class CompletionRequest {

    /**
     * 添加消息的方便方法
     * @param prompts 消息体
     */
    public void sendMessage(String... prompts) {
        if (prompt == null) {
            prompt = new ArrayList<>();
        }
        prompt.addAll(Arrays.asList(prompts));
    }




    

    /**
     * model
     * string
     * Required
     * ID of the model to use. You can use the List models API to see all of your available models, or see our Model overview for descriptions of them.
     */
    private String model;

    /**
     * prompt
     * string or array
     * Optional
     * Defaults to <|endoftext|>
     * The prompt(s) to generate completions for, encoded as a string, array of strings, array of tokens, or array of token arrays.
     * Note that <|endoftext|> is the document separator that the model sees during training, so if a prompt is not specified the model will generate as if from the beginning of a new document.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> prompt;

    /**
     * suffix
     * string
     * Optional
     * Defaults to null
     * The suffix that comes after a completion of inserted text.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String suffix;

    /**
     * max_tokens
     * integer
     * Optional
     * Defaults to 16
     * The maximum number of tokens to generate in the completion.
     * The token count of your prompt plus cannot exceed the model's context length. Most models have a context length of 2048 tokens (except for the newest models, which support 4096). max_tokens
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    /**
     * temperature
     * number
     * Optional
     * Defaults to 1
     * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic.
     * We generally recommend altering this or but not both. top_p
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = DoubleContextualSerializer.class)
    @Precision(precision = 1)
    private Double temperature;

    /**
     * top_p
     * number
     * Optional
     * Defaults to 1
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     * We generally recommend altering this or but not both. Temperature
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = DoubleContextualSerializer.class)
    @Precision(precision = 1)
    @JsonProperty("top_p")
    private Double topP;

    /**
     * n
     * integer
     * Optional
     * Defaults to 1
     * How many completions to generate for each prompt.
     * Note: Because this parameter generates many completions, it can quickly consume your token quota. Use carefully and ensure that you have reasonable settings for and .max_tokensstop
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer n;

    /**
     * stream
     * boolean
     * Optional
     * Defaults to false
     * Whether to stream back partial progress. If set, tokens will be sent as data-only server-sent events as they become available, with the stream terminated by a message.data: [DONE]
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean stream = false;

    /**
     * logprobs
     * integer
     * Optional
     * Defaults to null
     * Include the log probabilities on the most likely tokens, as well the chosen tokens. For example, if is 5, the API will return a list of the 5 most likely tokens. The API will always return the of the sampled token, so there may be up to elements in the response.logprobslogprobslogproblogprobs+1
     * The maximum value for is 5. If you need more than this, please contact us through our Help center and describe your use case.logprobs
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer logprobs;

    /**
     * echo
     * boolean
     * Optional
     * Defaults to false
     * Echo back the prompt in addition to the completion
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean echo;

    /**
     * stop
     * string or array
     * Optional
     * Defaults to null
     * Up to 4 sequences where the API will stop generating further tokens. The returned text will not contain the stop sequence.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> stop;

    /**
     * presence_penalty
     * number
     * Optional
     * Defaults to 0
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model's likelihood to talk about new topics.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = DoubleContextualSerializer.class)
    @Precision(precision = 1)
    @JsonProperty("presence_penalty")
    private Double presencePenalty;

    /**
     * frequency_penalty
     * number
     * Optional
     * Defaults to 0
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model's likelihood to repeat the same line verbatim.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = DoubleContextualSerializer.class)
    @Precision(precision = 1)
    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;

    /**
     * best_of
     * integer
     * Optional
     * Defaults to 1
     * Generates completions server-side and returns the "best" (the one with the highest log probability per token). Results cannot be streamed.best_of
     * When used with , controls the number of candidate completions and specifies how many to return – must be greater than .nbest_ofnbest_ofn
     * Note: Because this parameter generates many completions, it can quickly consume your token quota. Use carefully and ensure that you have reasonable settings for and .max_tokensstop
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("best_of")
    private Integer bestOf;

    // ! "Waiting for the official to fix the bug." //
    /**
     * logit_bias
     * map
     * Optional
     * Defaults to null
     * Modify the likelihood of specified tokens appearing in the completion.
     * Accepts a json object that maps tokens (specified by their token ID in the GPT tokenizer) to an associated bias value from -100 to 100. You can use this tokenizer tool (which works for both GPT-2 and GPT-3) to convert text to token IDs. Mathematically, the bias is added to the logits generated by the model prior to sampling. The exact effect will vary per model, but values between -1 and 1 should decrease or increase likelihood of selection; values like -100 or 100 should result in a ban or exclusive selection of the relevant token.
     * As an example, you can pass to prevent the <|endoftext|> token from being generated.{"50256": -100}
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("logit_bias")
    private Map<String, Integer> logitBias = null;

    /**
     * user
     * string
     * Optional
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. Learn more.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String user;

}
