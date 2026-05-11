import OpenAI from "openai";

const client = new OpenAI({
    apiKey: process.env.GROQ_API_KEY,
    baseURL: "https://api.groq.com/openai/v1",
});
const stream = await client.chat.completions.create({
    model: "openai/gpt-oss-20b",
    messages: [{ role: "user", content: "Explain the importance of fast language models" }],
    stream: true,
});
for await (const chunk of stream) {
    const delta = chunk.choices?.[0]?.delta?.content;
    if (delta) process.stdout.write(delta);
}
process.stdout.write("\n");
