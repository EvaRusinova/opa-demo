package my_policy_package

default decision = "unknown"

decision = "standard" {
    input == 1
}

decision = "pro" {
    input == 2
}

decision = "premium" {
    input == 3
}

allow {
    decision == "standard"
}

allow {
    decision == "pro"
}

allow {
    decision == "premium"
}
