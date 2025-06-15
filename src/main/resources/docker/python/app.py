from flask import Flask, request, jsonify
from transformers import pipeline

app = Flask(__name__)
classifier = pipeline("text-classification", model="unitary/toxic-bert")

@app.route('/analyze', methods=['POST'])
def analyze_comment():
    data = request.get_json()
    comment = data.get("comment")
    if not comment:
        return jsonify({"error": "Missing comment"}), 400

    result = classifier(comment)[0]
    return jsonify({
        "label": result['label'],
        "score": result['score'],
        "is_hateful": result['label'] == "toxic" and result['score'] > 0.8
    })

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
