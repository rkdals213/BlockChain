pragma solidity ^0.5.12;
import "./Ownable.sol";

interface RatingRecordInterface {
    function addRating(
        address getter,
        address purchaseContractAddress,
        uint256 score
    ) external;
}

contract RatingRecord is RatingRecordInterface, Ownable {
    struct Rating {
        address evaluator;
        uint256 score;
    }

    //특정 판매자 purchase의 평가
    mapping(address => mapping(address => Rating)) ratingRecords;
    mapping(address => uint256) ratingCounts;

    function addRating(
        address getter,
        address purchaseContractAddress,
        uint256 score
    ) external {
        ratingRecords[getter][purchaseContractAddress] = Rating(
            msg.sender,
            score
        );

        uint256 ratingCount = ratingCounts[getter];
        ratingCounts[getter] = uint256(ratingCount + 1);
    }
}
